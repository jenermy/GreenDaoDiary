package com.example.greendaoorm;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author wanlijun
 * @description 实体类,对应数据库的表的字段，GreenDao自动根据实体类属性值创建表字段
 * @time 2018/1/23 17:41
 */
@Entity
public class Diary {
    @Id
    private Long id;
    @Property(nameInDb = "TITLE")
    private String title;
    @Property(nameInDb = "CONTENT")
    private String content;
    @Property(nameInDb = "TIME")
    private String time;
    @Generated(hash = 941819646)
    public Diary(Long id, String title, String content, String time) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
    }
    @Generated(hash = 112123061)
    public Diary() {
    }
    //以下get和set方法编译的时候自动生成
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
