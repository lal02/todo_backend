# Todo List Backend Service

## Motivation
I am working on this project because I couldn't find a quick and simple todo list app that includes only the absolute necessities. 
The focus is on creating a backend that can be used from different applications and device types with the simple feature of creating tasks that are grouped by categories.

## Entities
### Category
A `Category` has:
- An `id`
- A `name`
- A `default_priority` for the tasks that it contains
### Task
Each `Task` has:
- An `id`
- A `name`
- An optional `priority` (which can be different from the default one of the category)
- An optional `Category` (if none is assigned, it will be part of the "ungrouped" tasks)
- A `status`
- An optional `deadline`
- An optional `deadline-notification` time
- An optional `repetition` (if the task is known to be repetitive, like household tasks)
- An optional `note`

## Planned Features
- Create and edit categories (done)
- Create, edit, and change the status of tasks (done)
- Email notifications about tasks close to the deadline
- Export and import of all tasks via JSON file
- Output with multiple sorting options: task priority, task priority and category, category, etc.
- Later on: Offline synchronization







