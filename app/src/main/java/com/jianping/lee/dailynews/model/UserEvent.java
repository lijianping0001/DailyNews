package com.jianping.lee.dailynews.model;

/**
 * @fileName: UserEvent
 * @Author: Li Jianping
 * @Date: 2016/12/28 16:37
 * @Description:
 */
public class UserEvent {
    private String id;
    private String name;
    private boolean show;

    public UserEvent(){
    }

    public UserEvent(String id, String name, boolean show){
        this.id = id;
        this.name = name;
        this.show = show;
    }

    public UserEvent(String id, String name){
        this.id = id;
        this.name = name;
    }

    public UserEvent(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
