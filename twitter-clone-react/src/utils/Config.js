// config.js
//const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:3000';
//const SECRET_KEY = process.env.REACT_APP_SECRET_KEY;
const AUTH_REST_API_URL = 'http://localhost:9010/api/auth';
const FOLLOWERS_REST_API_URL = 'http://localhost:9010/api/followers';
const FOLLOWING_REST_API_URL = 'http://localhost:9010/api/following';
const FOLLOWS_REST_API_URL = 'http://localhost:9010/api/follows';
const BASE_REST_API_URL = 'http://localhost:9010/api/users';

export const Config = {
  //apiUrl: API_URL,
  //secretKey: SECRET_KEY,
  BASE_REST_API_URL: BASE_REST_API_URL,
  AUTH_REST_API_URL: AUTH_REST_API_URL,
  FOLLOWERS_REST_API_URL: FOLLOWERS_REST_API_URL,
  FOLLOWING_REST_API_URL: FOLLOWING_REST_API_URL,
  FOLLOWS_REST_API_URL: FOLLOWS_REST_API_URL,
};

// 다른 파일에서 사용
//import { config } from './Config';
//console.log(config.apiUrl); // 환경변수 값 또는 'http://localhost:3000'
