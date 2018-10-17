const faker = require("faker");
faker.locale = "fr";

module.exports = {
  path: '/api/city',
  method: 'GET',
  status: (req, res, next) => {
    if(req.params.id === 'error') {
      res.status(500);
    }
    next();
  },
  template: (params, query, body) => {
    const cities = [];
    for (let i = 0; i < 100; i++) {
      cities.push({id: faker.random.number, name: faker.address.city, postalCode: faker.random.number})
    }
    return cities;
  }
};
