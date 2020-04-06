package cn.cstqb.exam.testmaker.reporting;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/3/30
 * Time: 20:36
 */
public class QuestionTaskEntry {
    private EntryType type;
    private long total;
    private long pending;

    public QuestionTaskEntry(EntryType type, long total, long pending) {
        this.type = type;
        this.total = total;
        this.pending = pending;
    }

    public QuestionTaskEntry() {
    }

    public EntryType getType() {
        return type;
    }

    public void setType(EntryType type) {
        this.type = type;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPending() {
        return pending;
    }

    public void setPending(long pending) {
        this.pending = pending;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionTaskEntry that = (QuestionTaskEntry) o;

        return Objects.equal(this.type, that.type) &&
                Objects.equal(this.total, that.total) &&
                Objects.equal(this.pending, that.pending);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, total, pending);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("role", type)
                .add("total", total)
                .add("pending", pending)
                .toString();
    }

    public static enum EntryType {
        ALL,
        AUTHOR,
        REVIEWER,
        QA
    }
}
