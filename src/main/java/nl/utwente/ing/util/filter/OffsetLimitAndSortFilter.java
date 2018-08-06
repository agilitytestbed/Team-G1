package nl.utwente.ing.util.filter;

import nl.utwente.ing.exception.InvalidLimitException;
import nl.utwente.ing.exception.InvalidOffsetException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.io.Serializable;
import java.util.Objects;


public class OffsetLimitAndSortFilter implements Pageable, Serializable {

    private static final long serialVersionUID = -8946854157848516849L;

    private int limit;
    private long offset;
    private final Sort sort;


    public OffsetLimitAndSortFilter(long offset, int limit, Sort sort) {
        if (offset < 0) {
            throw new InvalidOffsetException();
        }

        if (limit < 1) {
            throw new InvalidLimitException();
        }
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    public OffsetLimitAndSortFilter(int offset, int limit, Sort.Direction direction, String... properties) {
        this(offset, limit, Sort.by(direction, properties));
    }

    public OffsetLimitAndSortFilter(int offset, int limit) {
        this(offset, limit,  Sort.by(Sort.Direction.ASC,"id"));
    }

    @Override
    public int getPageNumber() {
        return (int) (offset / limit);
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetLimitAndSortFilter(getOffset() + getPageSize(), getPageSize(), getSort());
    }

    public OffsetLimitAndSortFilter previous() {
        return hasPrevious() ?
               new OffsetLimitAndSortFilter(getOffset() - getPageSize(), getPageSize(), getSort())
                             : this;
    }


    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetLimitAndSortFilter(0, getPageSize(), getSort());
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OffsetLimitAndSortFilter that = (OffsetLimitAndSortFilter) o;
        return limit == that.limit &&
                offset == that.offset &&
                Objects.equals(sort, that.sort);
    }

    @Override
    public int hashCode() {

        return Objects.hash(limit, offset, sort);
    }

    @Override
    public String toString() {
        return "OffsetLimitAndSortFilter{" +
                "limit=" + limit +
                ", offset=" + offset +
                ", sort=" + sort +
                '}';
    }
}