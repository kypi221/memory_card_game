package com.kypi.demoproject.domain.entities;

public class MemoryCard {

    public enum Status{
        None,
        Complete,
        ToolFaceUp,
    }


    public int resourceId;
    public Status status = Status.None;
}
