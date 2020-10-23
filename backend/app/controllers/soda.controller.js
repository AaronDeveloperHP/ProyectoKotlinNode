const db = require("../models");
const Soda = db.sodas;
const Op = db.Sequelize.Op;

// Create and Save a new Soda
// req --> request (contains the body)
exports.create = (req, res) => {
  // Validate request
  if (!req.body.productName || !req.body.productQuantity || !req.body.productPack || !req.body.productFormat) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
    return;
  }

  // Create a Soda
  const soda = {
    productName: req.body.productName,
    productQuantity: req.body.productQuantity,
    productFormat: req.body.productFormat,
    productPack: req.body.productPack
  };

  // Save Soda in the database
  Soda.create(soda)
    .then(data => {
      res.send(data);
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while creating the Soda."
      });
    });
};

// Retrieve all Sodas from the database.
exports.findAll = (req, res) => {
    Soda.findAll()
      .then(data => {
        res.send(data);
      })
      .catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while retrieving sodas."
        });
      });
};

// Find a single Bicycle with an id
exports.findOne = (req, res) => {
  let id = req.params.id;
  Soda.findByPk(id)
    .then(data => {
      console.log("estos son los datos")
      console.log(data);
      if(!data){
        res.status(400).send({
          message:
            "No Soda found with that id"
        })
      }
      res.send(data);
      return;
    })
    .catch(err => {
      console.log(err.message);
      console.log("hola");
      res.status(500).send({
        message:
          err.message || "Some error occurred while retrieving soda with id"
      });
      return;
    });
};

// Update a Soda by the id in the request
exports.update = (req, res) => {
  const id = req.params.id;

  Soda.update(req.body, {
    where: { id: id }
  })
    .then(num => {
      if (num == 1) {
        res.send({
          message: "Soda was updated successfully."
        });
      } else {
        res.send({
          message: `Cannot update Soda with id=${id}. Maybe Soda was not found or req.body is empty!`
        });
      }
    })
    .catch(err => {
      res.status(500).send({
        message: "Error updating Soda with id=" + id
      });
    });
};

// Delete a Tutorial with the specified id in the request
exports.delete = (req, res) => {
  const id = req.params.id;

  Soda.destroy({
    where: { id: id }
  })
    .then(num => {
      if (num == 1) {
        res.send({
          message: "Soda was deleted successfully!"
        });
      } else {
        res.send({
          message: `Cannot delete Soda with id=${id}. Maybe Soda was not found!`
        });
      }
    })
    .catch(err => {
      res.status(500).send({
        message: "Could not delete Soda with id=" + id
      });
    });
};

// Delete all Bicycles from the database.
exports.deleteAll = (req, res) => {
  Soda.destroy({
    where: {},
    truncate: false
  })
    .then(nums => {
      res.send({ message: `${nums}Soda were deleted successfully!` });
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while removing all Soda."
      });
    });
};