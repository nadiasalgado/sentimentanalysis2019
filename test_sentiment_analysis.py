import unittest
import sentiment_analysis

class KnownValues(unittest.TestCase):
    
    #Test areaofcircle()

    def test_toshow_sentimentanalysisresults(self):
        #Capture results of the function
        result = sentiment_analysis.sentimentanalysis()
        
        #Check for expected output, entry: 10
        expected = ['positive','positive','positive','neutral','positive','negative','positive','negative','positive','positive','negative','negative','positive',
                    'neutral','positive','neutral','positive','neutral','neutral','negative','positive']
        self.assertEqual(expected, result)

# Run the tests
if __name__ == '__main__':
    unittest.main()