/*
 * This file is generated by jOOQ.
 */
package db.tables.records;


import db.tables.Takerevision;

import java.sql.Timestamp;

import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TakerevisionRecord extends UpdatableRecordImpl<TakerevisionRecord> implements Record6<Integer, Integer, Timestamp, String, String, JSONB> {

    private static final long serialVersionUID = 42458396;

    /**
     * Setter for <code>public.takerevision.id</code>.
     */
    public TakerevisionRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.takerevision.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.takerevision.parent_id</code>.
     */
    public TakerevisionRecord setParentId(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.takerevision.parent_id</code>.
     */
    public Integer getParentId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.takerevision.created_at</code>.
     */
    public TakerevisionRecord setCreatedAt(Timestamp value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.takerevision.created_at</code>.
     */
    public Timestamp getCreatedAt() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>public.takerevision.created_ip</code>.
     */
    public TakerevisionRecord setCreatedIp(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.takerevision.created_ip</code>.
     */
    public String getCreatedIp() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.takerevision.title</code>.
     */
    public TakerevisionRecord setTitle(String value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>public.takerevision.title</code>.
     */
    public String getTitle() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.takerevision.blocks</code>.
     */
    public TakerevisionRecord setBlocks(JSONB value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>public.takerevision.blocks</code>.
     */
    public JSONB getBlocks() {
        return (JSONB) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, Integer, Timestamp, String, String, JSONB> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Integer, Integer, Timestamp, String, String, JSONB> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Takerevision.TAKEREVISION.ID;
    }

    @Override
    public Field<Integer> field2() {
        return Takerevision.TAKEREVISION.PARENT_ID;
    }

    @Override
    public Field<Timestamp> field3() {
        return Takerevision.TAKEREVISION.CREATED_AT;
    }

    @Override
    public Field<String> field4() {
        return Takerevision.TAKEREVISION.CREATED_IP;
    }

    @Override
    public Field<String> field5() {
        return Takerevision.TAKEREVISION.TITLE;
    }

    @Override
    public Field<JSONB> field6() {
        return Takerevision.TAKEREVISION.BLOCKS;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getParentId();
    }

    @Override
    public Timestamp component3() {
        return getCreatedAt();
    }

    @Override
    public String component4() {
        return getCreatedIp();
    }

    @Override
    public String component5() {
        return getTitle();
    }

    @Override
    public JSONB component6() {
        return getBlocks();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getParentId();
    }

    @Override
    public Timestamp value3() {
        return getCreatedAt();
    }

    @Override
    public String value4() {
        return getCreatedIp();
    }

    @Override
    public String value5() {
        return getTitle();
    }

    @Override
    public JSONB value6() {
        return getBlocks();
    }

    @Override
    public TakerevisionRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public TakerevisionRecord value2(Integer value) {
        setParentId(value);
        return this;
    }

    @Override
    public TakerevisionRecord value3(Timestamp value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public TakerevisionRecord value4(String value) {
        setCreatedIp(value);
        return this;
    }

    @Override
    public TakerevisionRecord value5(String value) {
        setTitle(value);
        return this;
    }

    @Override
    public TakerevisionRecord value6(JSONB value) {
        setBlocks(value);
        return this;
    }

    @Override
    public TakerevisionRecord values(Integer value1, Integer value2, Timestamp value3, String value4, String value5, JSONB value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TakerevisionRecord
     */
    public TakerevisionRecord() {
        super(Takerevision.TAKEREVISION);
    }

    /**
     * Create a detached, initialised TakerevisionRecord
     */
    public TakerevisionRecord(Integer id, Integer parentId, Timestamp createdAt, String createdIp, String title, JSONB blocks) {
        super(Takerevision.TAKEREVISION);

        set(0, id);
        set(1, parentId);
        set(2, createdAt);
        set(3, createdIp);
        set(4, title);
        set(5, blocks);
    }
}
