'''
Created on Oct 21, 2013

@author: dhanyatha.manjunath
'''
import networkx as nx
import math
import gc
mygraph=nx.read_edgelist("E:/Social Media Mining/Project/Crawler Run/edge-list.txt", delimiter=',', create_using=nx.Graph())
newgraph=nx.Graph()
###########################################################
for e in mygraph.edges_iter():
    first=e[0]
    second=e[1]
    Di=mygraph.degree(first)
    Dj=mygraph.degree(second)
    w=math.fabs(Di-Dj)
    newgraph.add_edge(first, second, weight=w)
############################################################# 
del mygraph
gc.collect()
T=nx.minimum_spanning_tree(newgraph)
del newgraph
gc.collect()
print("Minimum Spanning Tree is")
print(T.edges(data=True))
#############################################################
Total_cost=0
loop_count=0
for x in T.edges_iter(data=True):
    first=x[0]
    second=x[1]
    wx=T.edge[first][second]['weight']
    Total_cost=Total_cost+wx
    loop_count=loop_count+1

print("Total Cost"+ str(Total_cost))
print("Loop Count" + str(loop_count))