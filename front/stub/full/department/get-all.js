const faker = require("faker");
faker.locale = "fr";

module.exports = {
  path: '/api/department',
  method: 'GET',
  status: (req, res, next) => {
    if(req.params.id === 'error') {
      res.status(500);
    }
    next();
  },
  template: (params, query, body) => {
    const results = [];
    for (let i = 0; i < 100; i++) {
      results.push({id: faker.random.number(), name: faker.address.state()});
    }
    return {results};
  }
};
