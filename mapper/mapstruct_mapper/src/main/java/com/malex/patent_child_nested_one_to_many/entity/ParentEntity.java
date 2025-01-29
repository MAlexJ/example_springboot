package com.malex.patent_child_nested_one_to_many.entity;

public record ParentEntity(ChildEntity total, ChildEntity paid) {}
