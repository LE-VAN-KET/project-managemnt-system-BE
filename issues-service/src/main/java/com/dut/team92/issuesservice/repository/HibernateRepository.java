package com.dut.team92.issuesservice.repository;

import java.util.List;

public interface HibernateRepository<T> {

    //Merge methods are meant to propagate detached entity state changes
    //if they are really needed

    <S extends T> S merge(S entity);

    <S extends T> S mergeAndFlush(S entity);

    <S extends T> List<S> mergeAll(Iterable<S> entities);

    <S extends T> List<S> mergeAllAndFlush(Iterable<S> entities);

    //Update methods are meant to force the detached entity state changes

    <S extends T> S update(S entity);

    <S extends T> S updateAndFlush(S entity);

    <S extends T> List<S> updateAll(Iterable<S> entities);

    <S extends T> List<S> updateAllAndFlush(Iterable<S> entities);

}
