'''
Created on Oct 21, 2013

@author: dhanyatha.manjunath
'''
import networkx as nx
mygraph=nx.read_edgelist("E:/Social Media Mining/Project/Crawler Run/edge-list.txt", delimiter=',', create_using=nx.Graph())
myedge_list=mygraph.edges()
bridge_count=0
edge_count=mygraph.number_of_edges()
############################################
loop_count=0
for e in mygraph.edges_iter():
    print(e)
    u=e[0]
    v=e[1]
    print(u)
    print(v)
    mygraph.remove_edge(u, v)
    boolean=nx.has_path(mygraph,u,v)
    print(boolean)
    if boolean==False:
        bridge_count=bridge_count+1
        print(bridge_count)
    mygraph.add_edge(u,v)
    loop_count=loop_count+1
################################################
print("Number of edges in the graph is"+ str(edge_count))
print("Number of for loop iterations"+ str(loop_count))
print("Number of bridges in the graph is"+ str(bridge_count))

