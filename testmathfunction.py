# Test mathfunction.py

import unittest
import mathfunction

class KnownValues(unittest.TestCase):
    
    #Test areaofcircle()
    
    def test_areaofcircle_for_radius10(self):
        #Capture results of the function
        result = mathfunction.areaofcircle(10)
        
        #Check for expected output, entry: 10
        expected = 314.15926535898
        self.assertEqual(expected, result)


    def test_areaofcircle_for_radis2(self):
    	result = mathfunction.areaofcircle(2)

    	#Check for expected output, entry: 10
    	expected = 12.56637061436
    	self.assertEqual(expected, result)

# Run the tests
if __name__ == '__main__':
    unittest.main()