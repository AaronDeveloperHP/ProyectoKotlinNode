module.exports = {
    HOST: "localhost",
    USER: "root",
    PASSWORD: "aaroncraft16",
    DB: "db_warehouse",
    dialect: "mysql",
    pool: {
      max: 5,
      min: 0,
      acquire: 30000,
      idle: 10000
    }
  };