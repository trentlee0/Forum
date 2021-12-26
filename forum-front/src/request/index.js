import axios from 'axios'

const baseURL = 'http://localhost:8089/'

axios.defaults.baseURL = baseURL

function request(config) {
  return new Promise((resolve, reject) => {
    axios(config).then(resp => {
      resolve(resp.data)
    }).catch(err => {
      reject(err)
    })
  })
}

export function getUploadURL() {
  return baseURL + '/upload'
}

export function getUploadAvatarURL() {
  return baseURL + '/uploadAvatar'
}

export function getUploadImageURL() {
  return baseURL + '/uploadImage'
}

export function getUploadVideoURL() {
  return baseURL + '/uploadVideo'
}

export function login(username, password) {
  return request({
    method: 'post',
    url: '/login',
    params: {
      username,
      password
    }
  })
}

export function register(username, password) {
  return request({
    method: 'post',
    url: '/register',
    params: {
      username,
      password
    }
  })
}

export function updateUser(token, uName, uPass, uGender, uBirthday, avatar) {
  return request({
    method: 'patch',
    url: '/user',
    params: {
      token,
      uName,
      uPass,
      uGender,
      uBirthday,
      avatar
    }
  })
}

export function getAllPlates() {
  return request({
    method: 'get',
    url: '/plates'
  })
}

export function getPlatePosts(pId, pageNum, pageSize) {
  return request({
    method: 'get',
    url: '/plates/get',
    params: {
      pId,
      pageNum,
      pageSize
    }
  })
}

export function getPost(postId) {
  return request({
    method: 'get',
    url: '/posts/' + postId
  })
}

export function getUser(token) {
  return request({
    method: 'get',
    url: '/user',
    params: {
      token
    }
  })
}

export function addApprove(uId, postId, token) {
  return request({
    method: 'post',
    url: '/approves/user-post',
    params: {
      token,
      uId,
      postId
    }
  })
}

export function addPost(token, uId, plateId, postName, content) {
  return request({
    method: 'post',
    url: '/posts',
    params: {
      token,
      uId,
      plateId,
      postName,
      content
    }
  })
}

export function logout(token) {
  return request({
    method: 'post',
    url: '/logout',
    params: {
      token
    }
  })
}

export function getUserPost(token, pageNum, pageSize) {
  return request({
    method: 'get',
    url: '/posts/get',
    params: {
      token,
      pageNum,
      pageSize
    }
  })
}

export function updateUserPost(token, uId, postId, content, postName) {
  return request({
    method: 'patch',
    url: '/posts',
    params: {
      token,
      uId,
      postId,
      content,
      postName
    }
  })
}

export function search(q) {
  return request({
    method: 'get',
    url: '/posts/search',
    params: {
      q
    }
  })
}

export function getAllUser(token, pageNum, pageSize) {
  return request({
    method: 'get',
    url: '/users',
    params: {
      token,
      pageNum,
      pageSize
    }
  })
}

export function updateUserAvailable(token, available, uId) {
  return request({
    method: 'get',
    url: '/users/available',
    params: {
      token,
      available,
      uId
    }
  })
}

export function getPlateUserByToken(token, pageNum, pageSize) {
  return request({
    method: 'get',
    url: '/plates/user',
    params: {
      token,
      pageNum,
      pageSize
    }
  })
}

export function getPlateUserByUId(uId) {
  return request({
    method: 'get',
    url: '/plates/userid',
    params: {
      uId
    }
  })
}

export function updateByUId(token, pId, pName) {
  return request({
    method: 'patch',
    url: '/plates/user-plate',
    params: {
      token,
      pId,
      pName
    }
  })
}

export function removeByPost(token, postId) {
  return request({
    method: 'delete',
    url: '/posts',
    params: {
      token,
      postId,
    }
  })
}

export function updateUserGrade(token, targetUId, grade, plateId) {
  return request({
    method: 'patch',
    url: '/users/grade',
    params: {
      token,
      targetUId,
      grade,
      plateId
    }
  })
}

export function removeUserPlate(token, plateId) {
  return request({
    method: 'patch',
    url: '/users/plate',
    params: {
      token,
      plateId
    }
  })
}

export function addComment(token, postId, uId, text) {
  return request({
    method: 'post',
    url: '/comments',
    params: {
      token,
      postId,
      uId,
      text
    }
  })
}

export function queryCommentsByUId(token, pageNum, pageSize) {
  return request({
    method: 'get',
    url: '/comments/user',
    params: {
      token,
      pageNum,
      pageSize
    }
  })
}

export function removeCommentsByCId(token, cId) {
  return request({
    method: 'delete',
    url: '/comments/' + cId,
    params: {
      token
    }
  })
}

export function updateComment(token, cId, text) {
  return request({
    method: 'patch',
    url: '/comments',
    params: {
      token,
      cId,
      text
    }
  })
}

export function addPlate(token, plateName) {
  return request({
    method: 'post',
    url: '/plates',
    params: {
      token,
      plateName
    }
  })
}

export function removePlate(token, plateId) {
  return request({
    method: 'delete',
    url: '/plates',
    params: {
      token,
      plateId
    }
  })
}
