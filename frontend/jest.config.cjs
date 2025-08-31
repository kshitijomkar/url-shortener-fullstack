// File: frontend/jest.config.js
module.exports = {
  // Use jsdom as the test environment to simulate a browser
  testEnvironment: 'jsdom',
  
  // A path to a module that runs some code to configure the testing framework before each test
  setupFilesAfterEnv: ['<rootDir>/jest.setup.js'],
  
  // Tell Jest how to handle file extensions
  moduleFileExtensions: ['js', 'jsx'],
  
  // Mock CSS files to prevent errors
  moduleNameMapper: {
    '\\.(css|less|scss|sass)$': 'identity-obj-proxy',
  },
};