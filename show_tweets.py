#Function to show all the tweets containing the inputted string x    
def show(x):
    
    with open('TestTweets.txt') as fp:

    	output = []
		
		for line in fp:
            if x in line:
                output.append(line.rstrip())
    return output
