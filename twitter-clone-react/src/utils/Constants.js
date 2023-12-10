// constants.js
export const Constants = {
  MAX_PASSWORD: 3,
  MAX_COUNT: 10,
  MAX_TWEETS: 100,
  REMAINED_TWEETS: 30,
}


export const API_URL = "https://api.example.com";
export const USER_ROLES = {
  ADMIN: 'admin',
  USER: 'user',
};

//import { constants } from './constants';
// import { Constants } from "../../utils/Constants"
//console.log( constants.MAX_COUNT);

// 다른 파일에서 상수 사용
//import { MAX_COUNT, API_URL, USER_ROLES } from './constants';
//console.log(MAX_COUNT); // 10
//console.log(API_URL); // "https://api.example.com"
//console.log(USER_ROLES.ADMIN); // "admin"