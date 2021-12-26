/**
 * 游客
 */
export const VISITOR = 0

/**
 * 普通用户
 */
export const USER = 1

/**
 * 版主
 */
export const MODERATOR = 2

/**
 * 超级管理员
 */
export const ADMIN = 3

export function getUserGrade(ugrade) {
  switch (ugrade) {
    case VISITOR: return '游客'
    case USER: return '普通用户'
    case MODERATOR: return '版主'
    case ADMIN: return '管理员'
  }
}
