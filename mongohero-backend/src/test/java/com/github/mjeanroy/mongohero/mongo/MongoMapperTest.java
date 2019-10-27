/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Mickael Jeanroy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.mjeanroy.mongohero.mongo;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class MongoMapperTest {

    private MongoMapper mongoMapper;

    @BeforeEach
    void setUp() {
        mongoMapper = new MongoMapper();
    }

    @Test
    void it_should_map_mongo_document_to_class_instance() {
        Document document = new Document();
        document.put("name", "John Doe");
        document.put("age", 20);
        document.put("male", true);

        Avenger avenger = mongoMapper.map(document, Avenger.class);

        assertThat(avenger).isNotNull();
        assertThat(avenger.name).isEqualTo("John Doe");
        assertThat(avenger.age).isEqualTo(20);
        assertThat(avenger.male).isTrue();
    }

    @Test
    void it_should_map_mongo_document_with_complex_document_attribute() {
        Document subDocument = new Document();
        subDocument.put("name", "Iron Man");
        subDocument.put("age", 30);
        subDocument.put("male", true);

        Document document = new Document();
        document.put("title", "Iron Man 2");
        document.put("hero", subDocument);

        Movie movie = mongoMapper.map(document, Movie.class);

        assertThat(movie).isNotNull();
        assertThat(movie.title).isEqualTo("Iron Man 2");
        assertThat(movie.hero).isNotNull();
        assertThat(movie.hero.name).isEqualTo("Iron Man");
        assertThat(movie.hero.age).isEqualTo(30);
        assertThat(movie.hero.male).isTrue();
    }

    @Test
    void it_should_map_mongo_document_with_given_map() {
        Document map = new Document();
        map.put("Iron Man 2", 4);
        map.put("Hulk", 2);
        map.put("Thor", 3);

        Document document = new Document();
        document.put("id", "1");
        document.put("ratings", map);

        MovieRatings instance = mongoMapper.map(document, MovieRatings.class);

        assertThat(instance).isNotNull();
        assertThat(instance.id).isEqualTo("1");
        assertThat(instance.ratings).hasSize(3)
            .contains(
                    entry("Iron Man 2", 4),
                    entry("Hulk", 2),
                    entry("Thor", 3)
            );
    }

    private static class Avenger {
        private String name;
        private int age;
        private boolean male;

        Avenger() {
        }
    }

    private static class Movie {
        private String title;
        private Avenger hero;

        Movie() {
        }
    }

    private static class MovieRatings {
        private String id;
        private Map<String, Integer> ratings;

        MovieRatings() {
        }
    }
}
