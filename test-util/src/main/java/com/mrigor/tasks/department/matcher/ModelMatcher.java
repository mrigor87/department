package com.mrigor.tasks.department.matcher;


import com.mrigor.tasks.department.TestUtil;
import org.junit.Assert;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * This class wrap every entity by Wrapper before assertEquals in order to compare them by comparator
 * Default comparator compare by String.valueOf(entity)
 *
 * @param <T> : Entity
 * @see JsonUtil
 */
public class ModelMatcher<T> {
    private static final Comparator DEFAULT_COMPARATOR =
            (Object expected, Object actual) -> expected == actual || String.valueOf(expected).equals(String.valueOf(actual));

    public Comparator<T> comparator;
    private Class<T> entityClass;

    public interface Comparator<T> {
        boolean compare(T expected, T actual);
    }

    public ModelMatcher(Class<T> entityClass) {
        this(entityClass, (Comparator<T>) DEFAULT_COMPARATOR);
    }

    public ModelMatcher(Class<T> entityClass, Comparator<T> comparator) {
        this.entityClass = entityClass;
        this.comparator = comparator;
    }

    /**
     * this class represents wrapped entity for comparing
     */
    private class Wrapper {
        private T entity;

        private Wrapper(T entity) {
            this.entity = entity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Wrapper that = (Wrapper) o;
            return entity != null ? comparator.compare(entity, that.entity) : that.entity == null;
        }

        @Override
        public String toString() {
            return String.valueOf(entity);
        }
    }

    /**
     * convert json data to entity
     * @param json data in format json
     * @return entity
     */
    public T fromJsonValue(String json) {
        return JsonUtil.readValue(json, entityClass);
    }

    /**
     * convert json data to collection of entities
     * @param json data in json format
     * @return collection of entities
     */
    private Collection<T> fromJsonValues(String json) {
        return JsonUtil.readValues(json, entityClass);
    }

    /**
     * convert json data from ResultActions to entity
     * @param action ResultActions
     * @return entity
     * @throws UnsupportedEncodingException
     */
    public T fromJsonAction(ResultActions action) throws UnsupportedEncodingException {
        return fromJsonValue(TestUtil.getContent(action));
    }

    /**
     * compare wrapped date with junit assert
     * @param expected
     * @param actual
     */
    public void assertEquals(T expected, T actual) {
        Assert.assertEquals(wrap(expected), wrap(actual));
    }

    public void assertCollectionEquals(Collection<T> expected, Collection<T> actual) {
        Assert.assertEquals(wrap(expected), wrap(actual));
    }

    /**
     * wrap entity
     * @param entity
     * @return wrapped entity
     */
    private Wrapper wrap(T entity) {
        return new Wrapper(entity);
    }

    /**
     * wraps collection of entities
     * @param collection
     * @return collection of wrapped entities
     */
    public List<Wrapper> wrap(Collection<T> collection) {
        return collection.stream().map(this::wrap).collect(Collectors.toList());
    }

    /**
     * builds ResultMatcher for expectation in MockMvc
     * @param expect entity
     * @return ResultMatcher
     */
    public final ResultMatcher contentMatcher(T expect) {
        return content().string(
                new TestMatcher<T>(expect) {
                    @Override
                    protected boolean compare(T expected, String body) {
                        Wrapper expectedForCompare = wrap(expected);
                        Wrapper actualForCompare = wrap(fromJsonValue(body));
                        return expectedForCompare.equals(actualForCompare);
                    }
                });
    }

    /**
     * builds ResultMatcher for expectation in MockMvc
     * @param expected a few entities
     * @return ResultMatcher
     */
    public final ResultMatcher contentListMatcher(T... expected) {
        return contentListMatcher(Arrays.asList(expected));
    }

    /**
     * builds ResultMatcher for expectation in MockMvc
     * @param expected list of entoties
     * @return ResultMatcher
     */
    public final ResultMatcher contentListMatcher(List<T> expected) {
        return content().string(
                new TestMatcher<List<T>>(expected) {
                    @Override
                    protected boolean compare(List<T> expected, String actual) {
                        List<Wrapper> expectedList = wrap(expected);
                        List<Wrapper> actualList = wrap(fromJsonValues(actual));
                        return expectedList.equals(actualList);
                    }
                });
    }

    public static <T> ModelMatcher<T> of(Class<T> entityClass, Comparator<T> comparator) {
        return new ModelMatcher<>(entityClass, comparator);
    }
}
