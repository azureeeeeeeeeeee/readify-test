import api from "./api"
import { setCookie } from "typescript-cookie"

export const login = async (username: string, password: string) => {
    const res = await api.post('/auth/login', {username, password});
    setCookie("token", res.data)
    console.log(res);
}

export const logout = async () => {
    console.log('logging out');
}

export const register = async (username: string, password: string, confirm_password: string) => {
    if (password !== confirm_password) {
        console.log('password doesnt match')
        return
    }

    const res = await api.post('/auth/logout', {username, password})
    console.log(res)
}