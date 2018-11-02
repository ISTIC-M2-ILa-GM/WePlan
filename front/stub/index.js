const dyson = require('dyson');
const path = require('path');

const options = {
  configDir: path.join(__dirname, 'full'),
  port: 8080,
  bodyParserJsonLimit: "15mb"
};

const configs = dyson.getConfigurations(options);
const appBefore = dyson.createServer(options);
const appAfter = dyson.registerServices(appBefore, options, configs);
