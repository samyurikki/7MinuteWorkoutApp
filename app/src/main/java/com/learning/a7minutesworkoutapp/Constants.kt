package com.learning.a7minutesworkoutapp

object Constants {
    fun defaultExerciseList():ArrayList<ExerciseModel>{
        val exerciseList = arrayListOf<ExerciseModel>()
        val jumpingJacks = ExerciseModel(
            1,"Jumping Jacks",R.drawable.ic_jumping_jacks, isCompleted = false,isSelected = false,
        )
        exerciseList.add(jumpingJacks)
        val highKnees = ExerciseModel(
            2,"High Knees",R.drawable.ic_high_knees_running_in_place, isCompleted = false,isSelected = false,
        )
        exerciseList.add(highKnees)
        val lungeExercise = ExerciseModel(
            3,"Lunge Exercise",R.drawable.ic_lunge,isCompleted = false,isSelected = false
        )
        exerciseList.add(lungeExercise)

        val plank = ExerciseModel(
            4,"Plank Exercise",R.drawable.ic_plank,isCompleted = false,isSelected = false
        )
        exerciseList.add(plank)

        val pushUp = ExerciseModel(
            5,"Push Up",R.drawable.ic_push_up,isCompleted = false,isSelected = false
        )
        exerciseList.add(pushUp)

        val pushUpAndRotation = ExerciseModel(
            6,"Push up and Rotation",R.drawable.ic_push_up_and_rotation,isCompleted = false,isSelected = false
        )
        exerciseList.add(pushUpAndRotation)

        val squat = ExerciseModel(
            7,"Squat",R.drawable.ic_squat,isCompleted = false,isSelected = false
        )
        exerciseList.add(squat)

        val stepUpOntoChair = ExerciseModel(
            8,"Step Up Onto chair",R.drawable.ic_step_up_onto_chair,isCompleted = false,isSelected = false
        )
        exerciseList.add(stepUpOntoChair)

        val triceps = ExerciseModel(
            9,"Triceps",R.drawable.ic_triceps_dip_on_chair,isCompleted = false,isSelected = false
        )
        exerciseList.add(triceps)

        val wallSit = ExerciseModel(
            10,"Wall Sit",R.drawable.ic_wall_sit,isCompleted = false,isSelected = false
        )
        exerciseList.add(wallSit)

        val abdominalCrunch = ExerciseModel(
            11,"Abdominal Crunch",R.drawable.ic_abdominal_crunch,isCompleted = false,isSelected = false
        )
        exerciseList.add(abdominalCrunch)

        val sidePlank = ExerciseModel(
            12,"Side Plank",R.drawable.ic_side_plank,isCompleted = false,isSelected = false
        )
        exerciseList.add(sidePlank)
        return exerciseList
    }
}