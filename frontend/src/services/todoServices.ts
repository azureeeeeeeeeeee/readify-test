import api from "./api"

export const getAllTodo = async () => {
    const res = await api.get('/todo')
    console.log(res)
}

export const addTodo = async (task: string) => {
    const res = await api.post("/todo", {task})
    console.log(res)
    return res
}

export const deleteTodo = async (id: number) => {
    const res = await api.delete(`/todo/${id}`)
    console.log(res)
}