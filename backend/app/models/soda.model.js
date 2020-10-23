module.exports = (sequelize, Sequelize) => {
    const Soda = sequelize.define("soda", {
      productName: {
        type: Sequelize.STRING
      },
      productQuantity: {
        type: Sequelize.INTEGER
      },
      productFormat:{
        type: Sequelize.STRING
      },
      productPack:{
        type: Sequelize.INTEGER
      }
      
    }, { timestamps: false});
  
    return Soda;
  };