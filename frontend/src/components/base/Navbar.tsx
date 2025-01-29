import { Box, Flex, Link, Button } from "@chakra-ui/react";
import { useState } from "react";

const Navbar = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(true);

  return (
    <Box bg="blue.500" px={4} py={2} boxShadow="md">
      <Flex justify="space-between" align="center" maxW="1200px" mx="auto">
        <Link href="/" fontSize="xl" fontWeight="bold" color="text">
          Readify
        </Link>

        {!isAuthenticated ? (
          <Button colorScheme="teal" size="sm" onClick={() => setIsAuthenticated(true)}>Login</Button>
        ) : (
          <Button colorScheme="teal" size="sm" onClick={() => setIsAuthenticated(false)}>Logout</Button>
        )}
      </Flex>
    </Box>
  );
};

export default Navbar;