/*
 * This file is generated by jOOQ.
 */
package db.tables.records;


import db.enums.Reaction;
import db.tables.Takereaction;

import java.sql.Timestamp;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TakereactionRecord extends UpdatableRecordImpl<TakereactionRecord> implements Record5<Integer, Integer, Reaction, Timestamp, String> {

    private static final long serialVersionUID = 1688083655;

    /**
     * Setter for <code>public.takereaction.take_id</code>.
     */
    public TakereactionRecord setTakeId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.takereaction.take_id</code>.
     */
    public Integer getTakeId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.takereaction.user_id</code>.
     */
    public TakereactionRecord setUserId(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.takereaction.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.takereaction.kind</code>.
     */
    public TakereactionRecord setKind(Reaction value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.takereaction.kind</code>.
     */
    public Reaction getKind() {
        return (Reaction) get(2);
    }

    /**
     * Setter for <code>public.takereaction.reacted_at</code>.
     */
    public TakereactionRecord setReactedAt(Timestamp value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.takereaction.reacted_at</code>.
     */
    public Timestamp getReactedAt() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>public.takereaction.reacted_ip</code>.
     */
    public TakereactionRecord setReactedIp(String value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>public.takereaction.reacted_ip</code>.
     */
    public String getReactedIp() {
        return (String) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record3<Integer, Integer, Reaction> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, Integer, Reaction, Timestamp, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Integer, Integer, Reaction, Timestamp, String> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Takereaction.TAKEREACTION.TAKE_ID;
    }

    @Override
    public Field<Integer> field2() {
        return Takereaction.TAKEREACTION.USER_ID;
    }

    @Override
    public Field<Reaction> field3() {
        return Takereaction.TAKEREACTION.KIND;
    }

    @Override
    public Field<Timestamp> field4() {
        return Takereaction.TAKEREACTION.REACTED_AT;
    }

    @Override
    public Field<String> field5() {
        return Takereaction.TAKEREACTION.REACTED_IP;
    }

    @Override
    public Integer component1() {
        return getTakeId();
    }

    @Override
    public Integer component2() {
        return getUserId();
    }

    @Override
    public Reaction component3() {
        return getKind();
    }

    @Override
    public Timestamp component4() {
        return getReactedAt();
    }

    @Override
    public String component5() {
        return getReactedIp();
    }

    @Override
    public Integer value1() {
        return getTakeId();
    }

    @Override
    public Integer value2() {
        return getUserId();
    }

    @Override
    public Reaction value3() {
        return getKind();
    }

    @Override
    public Timestamp value4() {
        return getReactedAt();
    }

    @Override
    public String value5() {
        return getReactedIp();
    }

    @Override
    public TakereactionRecord value1(Integer value) {
        setTakeId(value);
        return this;
    }

    @Override
    public TakereactionRecord value2(Integer value) {
        setUserId(value);
        return this;
    }

    @Override
    public TakereactionRecord value3(Reaction value) {
        setKind(value);
        return this;
    }

    @Override
    public TakereactionRecord value4(Timestamp value) {
        setReactedAt(value);
        return this;
    }

    @Override
    public TakereactionRecord value5(String value) {
        setReactedIp(value);
        return this;
    }

    @Override
    public TakereactionRecord values(Integer value1, Integer value2, Reaction value3, Timestamp value4, String value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TakereactionRecord
     */
    public TakereactionRecord() {
        super(Takereaction.TAKEREACTION);
    }

    /**
     * Create a detached, initialised TakereactionRecord
     */
    public TakereactionRecord(Integer takeId, Integer userId, Reaction kind, Timestamp reactedAt, String reactedIp) {
        super(Takereaction.TAKEREACTION);

        set(0, takeId);
        set(1, userId);
        set(2, kind);
        set(3, reactedAt);
        set(4, reactedIp);
    }
}
