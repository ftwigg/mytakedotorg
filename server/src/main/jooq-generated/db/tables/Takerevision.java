/*
 * This file is generated by jOOQ.
*/
package db.tables;


import db.Indexes;
import db.Keys;
import db.Public;
import db.bindings.PostgresInetBinding;
import db.bindings.PostgresJsonStringBinding;
import db.tables.records.TakerevisionRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Takerevision extends TableImpl<TakerevisionRecord> {

    private static final long serialVersionUID = -609306489;

    /**
     * The reference instance of <code>public.takerevision</code>
     */
    public static final Takerevision TAKEREVISION = new Takerevision();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TakerevisionRecord> getRecordType() {
        return TakerevisionRecord.class;
    }

    /**
     * The column <code>public.takerevision.id</code>.
     */
    public final TableField<TakerevisionRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('takerevision_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>public.takerevision.parent_id</code>.
     */
    public final TableField<TakerevisionRecord, Integer> PARENT_ID = createField("parent_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>public.takerevision.created_at</code>.
     */
    public final TableField<TakerevisionRecord, Timestamp> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>public.takerevision.created_ip</code>.
     */
    public final TableField<TakerevisionRecord, String> CREATED_IP = createField("created_ip", org.jooq.impl.DefaultDataType.getDefaultDataType("inet"), this, "", new PostgresInetBinding());

    /**
     * The column <code>public.takerevision.title</code>.
     */
    public final TableField<TakerevisionRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.takerevision.blocks</code>.
     */
    public final TableField<TakerevisionRecord, String> BLOCKS = createField("blocks", org.jooq.impl.DefaultDataType.getDefaultDataType("jsonb"), this, "", new PostgresJsonStringBinding());

    /**
     * Create a <code>public.takerevision</code> table reference
     */
    public Takerevision() {
        this(DSL.name("takerevision"), null);
    }

    /**
     * Create an aliased <code>public.takerevision</code> table reference
     */
    public Takerevision(String alias) {
        this(DSL.name(alias), TAKEREVISION);
    }

    /**
     * Create an aliased <code>public.takerevision</code> table reference
     */
    public Takerevision(Name alias) {
        this(alias, TAKEREVISION);
    }

    private Takerevision(Name alias, Table<TakerevisionRecord> aliased) {
        this(alias, aliased, null);
    }

    private Takerevision(Name alias, Table<TakerevisionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.TAKEREVISION_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TakerevisionRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TAKEREVISION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TakerevisionRecord> getPrimaryKey() {
        return Keys.TAKEREVISION_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TakerevisionRecord>> getKeys() {
        return Arrays.<UniqueKey<TakerevisionRecord>>asList(Keys.TAKEREVISION_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TakerevisionRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TakerevisionRecord, ?>>asList(Keys.TAKEREVISION__TAKEREVISION_PARENT_ID_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Takerevision as(String alias) {
        return new Takerevision(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Takerevision as(Name alias) {
        return new Takerevision(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Takerevision rename(String name) {
        return new Takerevision(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Takerevision rename(Name name) {
        return new Takerevision(name, null);
    }
}
