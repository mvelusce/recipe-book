package com.mvelusce.recipebook.loading

import javafx.concurrent.Task
import org.slf4j.LoggerFactory

class LoadWebAppTask(
        private val statusChecker: WebAppStatusChecker,
        private val url: String,
        private val attempts: Int = 10,
        private val retryMillis: Long = 100
) : Task<LoadStatus>() {

    private val logger = LoggerFactory.getLogger(LoadWebAppTask::class.java)

    companion object {
        val successMessage = "Your kitchen is ready!"
        val failedMessage = "Wops! Something went wrong. Please try again."
    }

    private val cookingPrepActivities = arrayOf(
            "Cleaning the dishes", "Heating up the oven", "Turning up the stove",
            "Boiling the water", "Leavening the dough", "Laying the table"
    )

    override fun call(): LoadStatus {
        logger.info("Loading task started")

        updateMessage("Getting your kitchen ready . . .")

        for (i: Int in 1..attempts) {

            logger.info("Checking web-app status for the $i-th time")
            val response = statusChecker.checkStatus(url)

            if (response == LoadStatus.SUCCESS) {
                logger.info("Attempt $i returned success")
                updateMessage(successMessage)
                return LoadStatus.SUCCESS
            }

            logger.debug("Web-app not ready. Sleeping again. Attempt: [$i]. Sleep for: [$retryMillis]")
            Thread.sleep(retryMillis)
            updateMessage("${cookingPrepActivities[attempts % cookingPrepActivities.size]} . . .")
        }
        logger.error("Web-app loading failed")
        updateMessage(failedMessage)
        return LoadStatus.FAILED
    }
}
