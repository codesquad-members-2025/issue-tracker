import createFetchMock from 'vitest-fetch-mock';
import { vi, beforeEach, afterEach } from 'vitest';
import { cleanup } from '@testing-library/react';
import '@testing-library/jest-dom';

const fetchMocker = createFetchMock(vi);

fetchMocker.enableMocks();

beforeEach(() => {
  fetchMocker.doMock();
});

afterEach(() => {
  cleanup();
});
