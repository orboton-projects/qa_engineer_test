const { defineConfig } = require('cypress');

module.exports = defineConfig({
  e2e: {
    baseUrl: 'https://www.saucedemo.com',
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
    specPattern: 'cypress/integration/**/*.spec.js',
    supportFile: 'cypress/support/index.js',
  },
  fixturesFolder: 'cypress/fixtures',
  video: false, // disable video recording
  videosFolder: 'cypress/videos', // provide a valid folder path (even if video recording is disabled)
});