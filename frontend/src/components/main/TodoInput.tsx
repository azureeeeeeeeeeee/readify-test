import { useState, useEffect } from "react";
import { Input } from "@chakra-ui/react";
import { Button } from "@chakra-ui/react"; 
import { addTodo } from "../../services/todoServices"

export default function TodoInput() {
  const [task, setTask] = useState("");
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    async function getTasks() {
    //   const fetchedTasks = await fetchTasks();
    //   setTasks(fetchedTasks);
        console.log('nice')
    }
    getTasks();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault()
    await addTodo(task)
    setTask("")
    setTasks([])
  };

  return (
    <div>
      <form onSubmit={handleSubmit} className="flex gap-2">
        <Input
          type="text"
          placeholder="Add a new task..."
          value={task}
          onChange={(e) => setTask(e.target.value)}
          className="flex-1"
        />
        <Button type="submit">Add</Button>
      </form>
      <ul className="mt-4">
        {tasks.map((t, index) => (
          <li key={index} className="p-2 border-b">{t}</li>
        ))}
      </ul>
    </div>
  );
}