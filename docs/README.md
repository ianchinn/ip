# Yapper User Guide

![Yapper sample](./GUI%20sample.png)

## Adding Tasks!

3 Types of supported tasks:
1. Todo
2. Deadline
3. Event

## TODO Tasks
Simplest types of tasks! Just a description will do.

> todo *(task description)*

Example: `todo clean bedroom`

```
Aye. Big man gone do big man things:
    [T][] clean bedroom
Now you have 1 task in the list
```

## DEADLINE Tasks
Task with deadlines! Provide task description followed by deadline!

> deadline *(task description)* ***/by*** *deadline (YYYY/MM/DD)*

Example: `deadline finish assignment /by 2025/07/07`

```
Aye. Big man gone do big man things:
    [D][] finish assignment (by: Jul 07 2025)
Now you have 1 task in the list
```
## EVENT Tasks
Events! Provide event description followed by its duration!

> event *(task description)* ***/from*** *start (YYYY/MM/DD)* ***/to*** *end (YYYY/MM/DD)*

Example: `event roadshow /from 2025/07/07 /to 2025/07/12`

```
Aye. Big man gone do big man things:
    [E][] roadshow (from: Jul 07 2025 to: Jul 12 2025)
Now you have 1 task in the list
```
## Marking/Unmarking Completed Tasks
Once you are done with a task, simply tell yapper to mark it as done!
However, if you realize that it was NOT actually done, just tell yapper to unmark!

> mark *(task number)*

> unmark *(task number)*

Example: `mark 1`

```
Aight G. I've marked this task as done:
[T][X] Sample Task
```



Example: `unmark 1`

```
Whatever you say G:
[T][] Sample Task
```
## Deleting Tasks
Did not mean to save a task? Don't worry, ask yapper to delete it!

> delete *(task number)*

Example: `delete 5`

```
Aye. I done removed this:
    [T][] Task no.5
Now you have 4 task in the list
```
## Listing Tasks
Want to see what tasks you have on your plate? Ask yapper to list them!

> list

Example: `list`

```
Check it out blud:
1. [T][] Task 1
2. [D][] Deadline Task (by: Dec 31 2025)
3. [E][] Event (from: Jul 07 2025 to: Jul 08 2025)
```
## Finding Tasks
Have a long list of tasks? Want to find a certain one fast? Ask yapper to find it for you!

> find *(keyword)*

Example: `find clean`

```
I got what you looking for innit:
1. [T][] clean bedroom
2. [D][] clean pantry (by: Dec 12 2025)
```
## Updating Tasks
Saved a task with wrong details! Worry not, simply ask yapper to update with relevant info!

You may change the task name, deadlines or event durations with the following: */name, /by, /from, /to*.
> update *(task num)*  **/name** *newName*

Example: `update 1 /name newName`

```
Make up yo mind next time T_T:
[T][] newName
```

## Exiting yapper

Once done with yapper, use the command ***bye*** to exit the program!
> bye
