const PROXY_CONFIG = [
  {
    context: ["/ajax/**"],
    target: "http://localhost:8080",
    pathRewrite: {
      "^/ajax": ""
    },
    logLevel: "debug",
    secure: false
  },
  {
    context: ["/backend/login"],
    target: "http://localhost:8080/login",
    logLevel: "debug",
    secure: false
  }
];

module.exports = PROXY_CONFIG;
