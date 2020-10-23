module.exports = app => {
    const sodas = require("../controllers/soda.controller.js");
  
    var router = require("express").Router();
  
    // Create a new Bicycle
    router.post("/", sodas.create);
  
    // Retrieve all sodas
    router.get("/", sodas.findAll);
  
    // Retrieve a single bicycle with id
    router.get("/:id", sodas.findOne);
  
    // Update a bicycle with id
    router.put("/:id", sodas.update);
  
    // Delete a bicycle with id
    router.delete("/:id", sodas.delete);
  
    // Delete all sodas
    router.delete("/", sodas.deleteAll);
  
    app.use('/api/sodas', router);
  };