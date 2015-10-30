package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "io.github.w33vit.mystories.dao");
        Entity entity = schema.addEntity("Stories");

        entity.addIdProperty().autoincrement();
        entity.addStringProperty("title");
        entity.addStringProperty("content");
        entity.addDateProperty("stories_date");

        new DaoGenerator().generateAll(schema, args[0]);
    }

}
