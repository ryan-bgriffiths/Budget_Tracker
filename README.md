# Budget Tracker

Java implementation of a Budget Tacking application. 


## Features
- Track and update total Expenses 
- Set and track Goals for you savings and spendings
- Input and track current debts and progress towards payoff
- View monthly breakdowns 
- Sort expenses by date

## Structure

- src - *.java 
- *.txt (test files)

## How It Works

- Display start menu
    -1. Overall Expenses
    -2. Monthly Overview
    -3. Add Expense
    -4. Goals
    -5. Debt

-User selection navigates to each individual page
    - 1. Expense menu 
        - Add Expense 
        - Modify Expense 
        - Delete Expense 
    - 2. Monthly Breakdown
        - 'Breakdown display'
        - 1. Return to Home Page
        - 2. List next 5 items
        - 3. Change month 
    - 3. Add Expense 
        - Add Expense 
        - Modify Expense 
        - Delete Expense
    - 4. Goals
        - 1. Add Goal
        - 2. Modify Goal 
        - 3. Delete Goal
    - 5. Debt 
        - 1. Add Debt 
        - 2. Modify Debt 
        - 3. Delete Debt 

## GitHub Setup + Push Checklist (START HERE)

1. 
Accept repo access
   Check email / GitHub notifications
   Accept invite to the repository
   Clone the repo (DO NOT download ZIP)

Run
git clone https://github.com/OWNER/REPO_NAME.git
cd REPO_NAME

2. 
Confirm correct repo

Run
git remote -v
   (should show your GitHub repo URL)

3. 
Make sure you’re on main
Run
git branch (should show * main)
  If not:
Run
git checkout main

4. 
Pull latest version
Run
git pull origin main

5. 
Create your own branch
Run
git checkout -b dev-yourname-feature

  Example:
git checkout -b dev-ryan-homepage

Make a test change (edit any file, even add a comment)

  Then run:
git add .
git commit -m "test commit"

  Push your branch
git push -u origin dev-yourname-feature

  Confirm it worked
Go to GitHub
You should see your branch
You should see “Compare & pull request
