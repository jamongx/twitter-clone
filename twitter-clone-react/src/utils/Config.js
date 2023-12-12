// config.js
//const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:3000';
//const SECRET_KEY = process.env.REACT_APP_SECRET_KEY;
const AUTH_REST_API_URL = 'http://localhost:9010/api/v1/auth';
const FOLLOWS_REST_API_URL = 'http://localhost:9010/api/v1/follows';
const USERS_REST_API_URL = 'http://localhost:9010/api/v1/users';
const USER_PROFILES_REST_API_URL = 'http://localhost:9010/api/v1/user_profiles';

export const Config = {
  //apiUrl: API_URL,
  //secretKey: SECRET_KEY,
  USERS_REST_API_URL: USERS_REST_API_URL,
  USER_PROFILES_REST_API_URL: USER_PROFILES_REST_API_URL,
  AUTH_REST_API_URL: AUTH_REST_API_URL,
  FOLLOWS_REST_API_URL: FOLLOWS_REST_API_URL,
};

// 다른 파일에서 사용
//import { config } from './Config';
//console.log(config.apiUrl); // 환경변수 값 또는 'http://localhost:3000'
