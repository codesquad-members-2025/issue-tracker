const API_VERSION = '/api/v1';

export const API_BASE_URL = `${API_VERSION}`;

export const API = {
  ISSUES: `${API_BASE_URL}/issues`,
  POST_ISSUE: `${API_BASE_URL}/issues`,
  ISSUEDETAIL: `${API_BASE_URL}/issues`,
  LABELS: `${API_BASE_URL}/labels`,
  MILESTONES: `${API_BASE_URL}/milestones`,
  MILESTONES_SHORT: `${API_BASE_URL}/milestones/short`,
  USERS: `${API_BASE_URL}/users`,
  S3_PRESIGNED_URL: `${API_BASE_URL}/s3/presigned-url`,
};
