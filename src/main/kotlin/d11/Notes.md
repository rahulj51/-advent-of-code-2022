This was tricky and I could not solve this without help.

In the first part, I was getting the right answer with the sample data but a wrong answer with the actual input. After carefully debugging, this turned out to be due to an overlfow error as I was using Int. The funny thing about the bug was that it was very sneaky and not visible until I noticed that some "worry values" were large negative numbers.

The second part, of course, was tricky due to the exponential increase in worry value. Hence, a mathematical trick needed to be applied. 