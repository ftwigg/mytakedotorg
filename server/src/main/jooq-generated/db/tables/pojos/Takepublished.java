/*
 * This file is generated by jOOQ.
*/
package db.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


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
public class Takepublished implements Serializable {

    private static final long serialVersionUID = -1348893051;

    private Integer   id;
    private Integer   userId;
    private String    title;
    private String    titleSlug;
    private String    blocks;
    private Timestamp publishedAt;
    private String    publishedIp;
    private Timestamp deletedAt;
    private String    deletedIp;
    private Integer   countView;
    private Integer   countLike;
    private Integer   countBookmark;
    private Integer   countSpam;
    private Integer   countIllegal;
    private String    imageUrl;

    public Takepublished() {}

    public Takepublished(Takepublished value) {
        this.id = value.id;
        this.userId = value.userId;
        this.title = value.title;
        this.titleSlug = value.titleSlug;
        this.blocks = value.blocks;
        this.publishedAt = value.publishedAt;
        this.publishedIp = value.publishedIp;
        this.deletedAt = value.deletedAt;
        this.deletedIp = value.deletedIp;
        this.countView = value.countView;
        this.countLike = value.countLike;
        this.countBookmark = value.countBookmark;
        this.countSpam = value.countSpam;
        this.countIllegal = value.countIllegal;
        this.imageUrl = value.imageUrl;
    }

    public Takepublished(
        Integer   id,
        Integer   userId,
        String    title,
        String    titleSlug,
        String    blocks,
        Timestamp publishedAt,
        String    publishedIp,
        Timestamp deletedAt,
        String    deletedIp,
        Integer   countView,
        Integer   countLike,
        Integer   countBookmark,
        Integer   countSpam,
        Integer   countIllegal,
        String    imageUrl
    ) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.titleSlug = titleSlug;
        this.blocks = blocks;
        this.publishedAt = publishedAt;
        this.publishedIp = publishedIp;
        this.deletedAt = deletedAt;
        this.deletedIp = deletedIp;
        this.countView = countView;
        this.countLike = countLike;
        this.countBookmark = countBookmark;
        this.countSpam = countSpam;
        this.countIllegal = countIllegal;
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return this.id;
    }

    public Takepublished setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public Takepublished setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Takepublished setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTitleSlug() {
        return this.titleSlug;
    }

    public Takepublished setTitleSlug(String titleSlug) {
        this.titleSlug = titleSlug;
        return this;
    }

    public String getBlocks() {
        return this.blocks;
    }

    public Takepublished setBlocks(String blocks) {
        this.blocks = blocks;
        return this;
    }

    public Timestamp getPublishedAt() {
        return this.publishedAt;
    }

    public Takepublished setPublishedAt(Timestamp publishedAt) {
        this.publishedAt = publishedAt;
        return this;
    }

    public String getPublishedIp() {
        return this.publishedIp;
    }

    public Takepublished setPublishedIp(String publishedIp) {
        this.publishedIp = publishedIp;
        return this;
    }

    public Timestamp getDeletedAt() {
        return this.deletedAt;
    }

    public Takepublished setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public String getDeletedIp() {
        return this.deletedIp;
    }

    public Takepublished setDeletedIp(String deletedIp) {
        this.deletedIp = deletedIp;
        return this;
    }

    public Integer getCountView() {
        return this.countView;
    }

    public Takepublished setCountView(Integer countView) {
        this.countView = countView;
        return this;
    }

    public Integer getCountLike() {
        return this.countLike;
    }

    public Takepublished setCountLike(Integer countLike) {
        this.countLike = countLike;
        return this;
    }

    public Integer getCountBookmark() {
        return this.countBookmark;
    }

    public Takepublished setCountBookmark(Integer countBookmark) {
        this.countBookmark = countBookmark;
        return this;
    }

    public Integer getCountSpam() {
        return this.countSpam;
    }

    public Takepublished setCountSpam(Integer countSpam) {
        this.countSpam = countSpam;
        return this;
    }

    public Integer getCountIllegal() {
        return this.countIllegal;
    }

    public Takepublished setCountIllegal(Integer countIllegal) {
        this.countIllegal = countIllegal;
        return this;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Takepublished setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Takepublished (");

        sb.append(id);
        sb.append(", ").append(userId);
        sb.append(", ").append(title);
        sb.append(", ").append(titleSlug);
        sb.append(", ").append(blocks);
        sb.append(", ").append(publishedAt);
        sb.append(", ").append(publishedIp);
        sb.append(", ").append(deletedAt);
        sb.append(", ").append(deletedIp);
        sb.append(", ").append(countView);
        sb.append(", ").append(countLike);
        sb.append(", ").append(countBookmark);
        sb.append(", ").append(countSpam);
        sb.append(", ").append(countIllegal);
        sb.append(", ").append(imageUrl);

        sb.append(")");
        return sb.toString();
    }
}
