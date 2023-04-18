CREATE DATABASE IF NOT EXISTS recipemanager;

USE recipemanager;

CREATE TABLE IF NOT EXISTS recipes (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  instructions TEXT,
  vegetarian BOOLEAN NOT NULL,
  servings INT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ingredients (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS recipe_ingredients (
  id INT NOT NULL AUTO_INCREMENT,
  recipe_id INT NOT NULL,
  ingredient_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE,
  FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

INSERT INTO users (id, username, password, email) VALUES
(1, 'john_doe', 'password1', 'john_doe@example.com'),
(2, 'jane_smith', 'password2', 'jane_smith@example.com');

INSERT INTO recipes (id, name, instructions, vegetarian, servings) VALUES
(1, 'Pasta Carbonara', '1. Cook the pasta according to package instructions. Drain and set aside.\n2. In a large skillet, cook the bacon over medium heat until crispy. Remove with a slotted spoon and transfer to a paper towel-lined plate.\n3. In a small bowl, whisk together the eggs, Parmesan cheese, and black pepper.\n4. Add the pasta to the skillet with the bacon grease and toss to coat.\n5. Remove the skillet from heat and add the egg mixture, stirring quickly until the eggs are cooked and the pasta is coated with a creamy sauce. Serve immediately.', 0, 4),
(2, 'Vegetarian Chili', '1. In a large pot, heat the oil over medium-high heat. Add the onion, bell pepper, and garlic and cook until softened, about 5 minutes.\n2. Add the chili powder, cumin, paprika, salt, and pepper and stir to combine.\n3. Add the diced tomatoes (with juices), tomato paste, vegetable broth, and beans. Bring to a simmer and cook for 15-20 minutes.\n4. Stir in the corn and cook for an additional 5 minutes. Serve hot with your favorite toppings.', 1, 6),
(3, 'Beef Stroganoff', '1. Season the beef with salt and black pepper.\n2. In a large skillet, heat the oil over high heat. Add the beef and cook until browned, about 5-7 minutes.\n3. Remove the beef from the skillet and set aside. In the same skillet, add the onion and mushrooms and cook until softened, about 5 minutes.\n4. Add the beef back to the skillet and stir to combine.\n5. In a small bowl, whisk together the sour cream, Dijon mustard, and beef broth. Add to the skillet and stir to combine.\n6. Cook until the sauce is heated through and the beef is cooked to your liking. Serve hot over egg noodles or rice.', 0, 5);

INSERT INTO ingredients (id, name) VALUES
(1, 'Pasta'),
(2, 'Eggs'),
(3, 'Bacon'),
(4, 'Parmesan Cheese'),
(5, 'Tomatoes'),
(6, 'Onions'),
(7, 'Garlic'),
(8, 'Black Beans'),
(9, 'Kidney Beans'),
(10, 'Green Bell Pepper'),
(11, 'Ground Cumin'),
(12, 'Chili Powder'),
(13, 'Beef'),
(14, 'Sour Cream');

INSERT INTO recipe_ingredients (recipe_id, ingredient_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 9),
(2, 10),
(2, 11),
(2, 12),
(3, 1),
(3, 6),
(3, 7),
(3, 13),
(3, 14);