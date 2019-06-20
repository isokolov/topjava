package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import ru.javawebinar.topjava.repository.MealRepository;

public class MealService {

    private MealRepository repository;


    Meal create(Meal meal);

    void delete(int id) throws NotFoundException;

    Meal get(int id) throws NotFoundException;

    void update(int id) throws NotFoundException;

    List<Meal> getAll();
}