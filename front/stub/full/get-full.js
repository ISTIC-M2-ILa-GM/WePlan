const faker = require("faker");
faker.locale = "fr";

module.exports = {
  path: '/api/event',
  method: 'GET',
  status: (req, res, next) => {
    if (req.params.id === 'error') {
      res.status(500);
    }
    next();
  },
  template: (params, query, body) => {
    const results = [];
    for (let i = 0; i < 10; i++) {
      results.push(
        {
          id: faker.random.number(),
          canceled: false,
          date: new Date(),
          activity: {
            activityType: faker.lorem.word(),
            name: faker.lorem.word()
          },
          city: {
            name: faker.address.city()
          }
        }
      );
    }
    return {results, totalPages: 12, size: 10};
  }
};
