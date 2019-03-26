from textblob import TextBlob #Make sure you correctly install textblob

#Basic sentiment analysis function
def analysis(str):
    analysis = TextBlob(str)

    if analysis.sentiment.polarity > 0:
        return 'positive'
    if analysis.sentiment.polarity == 0:
        return 'neutral'
    else:
        return 'negative'

#Function to show all the tweets containing the inputted string x    
def show(x):
    
    with open('TestTweets.txt') as fp:
        for line in fp:
            if x in line:
                print(line)

# Main function to perform the sentiment analysis of hardcoded fake tweets in the TestTweets.txt file
def TwitterAnalysis():
    with open('TestTweets.txt') as fp: #Referring to the txt the file as fp
        
        strs = []
        
        for line in fp:
            strs.append(line)

    # Finding all the tweets with the specified words
        Witswords = ['Wits', 'University of the Witswatersrand', 'Wits University', 'wits'] #Words of a paremeter
        Wits = [s for s in strs if any(xs in s for xs in Witswords)]
        
        WitsPwords = ['WitsProtests']
        Witsprotests = [s for s in strs if any(xs in s for xs in WitsPwords)]
        
        Protestwords = ['protests', 'Protests', 'Protest', 'protest']
        Protests = [s for s in strs if any(xs in s for xs in Protestwords)]

        Upretoria = ['Tuks', 'UP', 'University of Pretoria', 'university of pretoria']
        UP = [s for s in strs if any(xs in s for xs in Upretoria)]
        
        uct = ['uct', 'UCT', 'University of Cape Town']
        UCT = [s for s in strs if any(xs in s for xs in uct)]

        AHab = ['Adam','Habib','A Habib']
        Adam = [s for s in strs if any(xs in s for xs in AHab)]

        for s in Wits:
            a = analysis(s)
            print (a)
        
        for s in Witsprotests:
            a = analysis(s)
            print (a)
        
        for s in Protests:
            a = analysis(s)
            print (a)
        
        for s in UP:
            a = analysis(s)
            print (a)
        
        for s in UCT:
            a = analysis(s)
            print (a)
            
        for s in Adam:
            a = analysis(s)
            print (a)
