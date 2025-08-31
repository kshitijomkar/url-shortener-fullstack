// File: frontend/jest.setup.js
import '@testing-library/jest-dom';
import { TextEncoder, TextDecoder } from 'util';

// This adds the TextEncoder and TextDecoder to the global scope for Jest
// which is required by react-router-dom v6.
global.TextEncoder = TextEncoder;
global.TextDecoder = TextDecoder;