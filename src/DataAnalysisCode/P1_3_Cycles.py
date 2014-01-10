'''
Created on Oct 21, 2013

@author: dhanyatha.manjunath
'''
import networkx as nx
mygraph=nx.read_edgelist("E:/Social Media Mining/Project/Crawler Run/edge-list.txt", delimiter=',', data="false")
node_count=mygraph.nodes()
tri=(list(nx.triangles(mygraph,node_count).values()))
count=0
i=0
for i in tri:
    count=count+i
num=(count/3)
print("Number of 3 cycles in the graph is")
print(num)