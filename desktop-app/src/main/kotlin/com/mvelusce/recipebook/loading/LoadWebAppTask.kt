package com.mvelusce.recipebook.loading

import org.slf4j.LoggerFactory

class LoadWebAppTask(
        private val statusChecker: WebAppStatusChecker,
        private val attempts: Int = 10,
        private val retryMillis: Long = 100
) {

    private val logger = LoggerFactory.getLogger(LoadWebAppTask::class.java)

    companion object {
        val successMessage = "Your kitchen is ready!"
        val failedMessage = "Wops! Something went wrong. Please try again."
    }

    private val cookingPrepActivities = arrayOf(
            "Cleaning the dishes", "Heating up the oven", "Turning up the stove",
            "Boiling the water", "Leavening the dough", "Laying the table"
    )

    fun checkStatusRepeatedly(url: String, updater: (String) -> Unit): LoadStatus {
        logger.info("Loading task started")

        updater("Getting your kitchen ready . . .")

        for (i: Int in 1..attempts) {

            logger.info("Checking web-app status for the $i-th time")
            val response = statusChecker.checkStatus(url)

            if (response == LoadStatus.SUCCESS) {
                logger.info("Attempt $i returned success")
                updater(successMessage)
                return LoadStatus.SUCCESS
            }

            logger.debug("Web-app not ready. Sleeping again. Attempt: [$i]. Sleep for: [$retryMillis]")
            Thread.sleep(retryMillis)
            updater("${cookingPrepActivities[(i % cookingPrepActivities.size)]} . . .")
        }
        logger.error("Web-app loading failed")
        updater(failedMessage)
        return LoadStatus.FAILED
    }
}
