# Overview
Sampling Methods Demonstration is a simple GUI application that can be used to compare the effectiveness of different sampling methods.
This program is based off of an exercise in the AP statistics textbook. A farm growing sunflowers is represented by a square grid of plots. 
There is a river above and below the farm. In plots closer to the river, the crop yield is higher because the plants recieve more water.
In order to know the true mean crop yield of the farm, the farmer decides to take a random sample of the plots on the farm – but which sampling method is best?

There are three options for sampling methods:
* **Simple random sample (SRS)**: Takes a random set of plots from the population. n=sqrt(width * height)
* **Stratified random sample**: Sample consists of one plot from each group set by the user.
* **Cluster sample**: Sample consists of all plots from one of the groups set by the user.

There are three different types of grouping used by stratified and cluster samples:
* **Group by row**: Each row of the grid represents one group.
* **Group by column**: Each column of the grid represents one group.
* **Custom grouping**: The user can specify the width and height of each group. Dimensions must be divisible by the dimensions of the grid.

# Taking samples
To take a sample, click the 'take samples' button. The number of samples can be changed in the text field to the left of the button. If the box labelled 'show sample visual' is checked,
samples will be taken over time and the user can see which plots are being selected by the samples.

# Graphs
For each sampling method tested, a new graph will appear showing the sampling distribution. It will also list descriptive statistics for the sampling distribution below the graph.
A red marker at the bottom of the plot represents the mean value.

# Other options
**Show data:** Shows the true crop yield for each plot.
**Show grid labels:** Shows the labels at the top and left side of the grid (e.g. A, B, C, ... and 1, 2, 3, ...)
**Resetting the sample space:** The user can specify dimensions for the new grid and reset the sample space with new random values.
