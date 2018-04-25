/*
 * This file is generated by jOOQ.
*/
package db.tables;


import db.Indexes;
import db.Keys;
import db.Public;
import db.bindings.PostgresInetBinding;
import db.tables.records.ConfirmaccountlinkRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
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
public class Confirmaccountlink extends TableImpl<ConfirmaccountlinkRecord> {

    private static final long serialVersionUID = -200069117;

    /**
     * The reference instance of <code>public.confirmaccountlink</code>
     */
    public static final Confirmaccountlink CONFIRMACCOUNTLINK = new Confirmaccountlink();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConfirmaccountlinkRecord> getRecordType() {
        return ConfirmaccountlinkRecord.class;
    }

    /**
     * The column <code>public.confirmaccountlink.code</code>.
     */
    public final TableField<ConfirmaccountlinkRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.CHAR(44).nullable(false), this, "");

    /**
     * The column <code>public.confirmaccountlink.created_at</code>.
     */
    public final TableField<ConfirmaccountlinkRecord, Timestamp> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>public.confirmaccountlink.expires_at</code>.
     */
    public final TableField<ConfirmaccountlinkRecord, Timestamp> EXPIRES_AT = createField("expires_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>public.confirmaccountlink.requestor_ip</code>.
     */
    public final TableField<ConfirmaccountlinkRecord, String> REQUESTOR_IP = createField("requestor_ip", org.jooq.impl.DefaultDataType.getDefaultDataType("inet"), this, "", new PostgresInetBinding());

    /**
     * The column <code>public.confirmaccountlink.username</code>.
     */
    public final TableField<ConfirmaccountlinkRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR(60).nullable(false), this, "");

    /**
     * The column <code>public.confirmaccountlink.email</code>.
     */
    public final TableField<ConfirmaccountlinkRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR(513).nullable(false), this, "");

    /**
     * Create a <code>public.confirmaccountlink</code> table reference
     */
    public Confirmaccountlink() {
        this(DSL.name("confirmaccountlink"), null);
    }

    /**
     * Create an aliased <code>public.confirmaccountlink</code> table reference
     */
    public Confirmaccountlink(String alias) {
        this(DSL.name(alias), CONFIRMACCOUNTLINK);
    }

    /**
     * Create an aliased <code>public.confirmaccountlink</code> table reference
     */
    public Confirmaccountlink(Name alias) {
        this(alias, CONFIRMACCOUNTLINK);
    }

    private Confirmaccountlink(Name alias, Table<ConfirmaccountlinkRecord> aliased) {
        this(alias, aliased, null);
    }

    private Confirmaccountlink(Name alias, Table<ConfirmaccountlinkRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.CONFIRMACCOUNTLINK_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ConfirmaccountlinkRecord> getPrimaryKey() {
        return Keys.CONFIRMACCOUNTLINK_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ConfirmaccountlinkRecord>> getKeys() {
        return Arrays.<UniqueKey<ConfirmaccountlinkRecord>>asList(Keys.CONFIRMACCOUNTLINK_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Confirmaccountlink as(String alias) {
        return new Confirmaccountlink(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Confirmaccountlink as(Name alias) {
        return new Confirmaccountlink(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Confirmaccountlink rename(String name) {
        return new Confirmaccountlink(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Confirmaccountlink rename(Name name) {
        return new Confirmaccountlink(name, null);
    }
}
